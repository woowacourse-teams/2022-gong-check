import { useEffect, useMemo, useRef, useState } from 'react';
import { useQuery } from 'react-query';
import { useLocation, useParams } from 'react-router-dom';

import DetailInfoModal from '@/components/user/DetailInfoModal';
import NameModal from '@/components/user/NameModal';

import useGoPreviousPage from '@/hooks/useGoPreviousPage';
import useModal from '@/hooks/useModal';
import useSectionCheck from '@/hooks/useSectionCheck';

import apis from '@/apis';

import { ID, SectionType } from '@/types';

const RE_FETCH_INTERVAL_TIME = 100;
const PROGRESS_BAR_DEFAULT_POSITION = 232;

const useTaskList = () => {
  const { spaceId, jobId, hostId } = useParams() as { spaceId: ID; jobId: ID; hostId: ID };

  const location = useLocation();
  const locationState = location.state as { jobName: string };

  const { openModal } = useModal();

  const { goPreviousPage } = useGoPreviousPage();

  const progressBarRef = useRef<HTMLDivElement>(null);

  const [isActiveSticky, setIsActiveSticky] = useState(false);

  const { data: sectionsData, refetch: getSections } = useQuery(
    ['sections', jobId],
    () => apis.getRunningTasks(jobId),
    {
      refetchInterval: RE_FETCH_INTERVAL_TIME,
      cacheTime: 0,
    }
  );

  const { data: spaceData } = useQuery(['space', jobId], () => apis.getSpace(spaceId));

  const { totalCount, checkedCount, percent, isAllChecked } = useSectionCheck(sectionsData?.sections || []);

  const onSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    openModal(
      <NameModal
        title="체크리스트 제출"
        detail="확인 버튼을 누르면 제출됩니다."
        placeholder="이름을 입력해주세요."
        buttonText="확인"
        jobId={jobId}
        hostId={hostId}
      />
    );
  };

  const onClickSectionDetail = (section: SectionType) => {
    openModal(<DetailInfoModal name={section.name} imageUrl={section.imageUrl} description={section.description} />);
  };

  useEffect(() => {
    const isActive = progressBarRef.current?.offsetTop! > PROGRESS_BAR_DEFAULT_POSITION;

    setIsActiveSticky(isActive);
  }, [progressBarRef.current?.offsetTop]);

  return {
    spaceData,
    getSections,
    onSubmit,
    goPreviousPage,
    totalCount,
    checkedCount,
    percent,
    isAllChecked,
    locationState,
    sectionsData,
    onClickSectionDetail,
    progressBarRef,
    isActiveSticky,
  };
};

export default useTaskList;
