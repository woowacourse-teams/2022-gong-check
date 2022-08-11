import { useEffect, useMemo, useRef, useState } from 'react';
import { useMutation, useQuery } from 'react-query';
import { useLocation, useParams } from 'react-router-dom';
import { EventSourcePolyfill } from 'event-source-polyfill'

import DetailInfoModal from '@/components/user/DetailInfoModal';
import NameModal from '@/components/user/NameModal';

import useGoPreviousPage from '@/hooks/useGoPreviousPage';
import useModal from '@/hooks/useModal';
import useSectionCheck from '@/hooks/useSectionCheck';

import apis from '@/apis';

import { ID, SectionType } from '@/types';
import { ApiTaskData } from '@/types/apis';

const PROGRESS_BAR_DEFAULT_POSITION = 232;

const useTaskList = () => {
  const { spaceId, jobId, hostId } = useParams() as { spaceId: ID; jobId: ID; hostId: ID };

  const location = useLocation();
  const locationState = location.state as { jobName: string };

  const { openModal } = useModal();

  const { goPreviousPage } = useGoPreviousPage();

  const progressBarRef = useRef<HTMLDivElement>(null);

  const [isActiveSticky, setIsActiveSticky] = useState(false);

  const [sectionsData, setSectionsData] = useState<ApiTaskData>({
    sections: [
      {
        id: 0,
        name: '',
        description: '',
        imageUrl: '',
        tasks: [{ id: 0, name: '', checked: false, description: '', imageUrl: '' }],
      },
    ],
  });

  const { data: spaceData } = useQuery(['space', jobId], () => apis.getSpace(spaceId));

  const { mutate: postSectionAllCheck } = useMutation((sectionId: ID) => apis.postSectionAllCheckTask(sectionId), {});

  const { sectionsAllCheckMap, totalCount, checkedCount, percent, isAllChecked } = useSectionCheck(
    sectionsData?.sections || []
  );

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

  const onClickSectionAllCheck = (sectionId: ID) => {
    postSectionAllCheck(sectionId);
  };

  useEffect(() => {
    const isActive = progressBarRef.current?.offsetTop! > PROGRESS_BAR_DEFAULT_POSITION;

    setIsActiveSticky(isActive);
  }, [progressBarRef.current?.offsetTop]);

  useEffect(() => {
    const sseTest = new EventSourcePolyfill(`http://192.168.1.4:8080/api/jobs/${jobId}/connect`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    });

    sseTest.addEventListener('connect', (e: any) => {
      const { data: receivedSections } = e;

      setSectionsData(JSON.parse(receivedSections));
    });

    sseTest.addEventListener('flip', (e: any) => {
      const { data: receivedSections } = e;

      setSectionsData(JSON.parse(receivedSections));
    });
  }, []);

  return {
    spaceData,
    onSubmit,
    goPreviousPage,
    totalCount,
    checkedCount,
    percent,
    sectionsAllCheckMap,
    isAllChecked,
    locationState,
    sectionsData,
    onClickSectionDetail,
    onClickSectionAllCheck,
    progressBarRef,
    isActiveSticky,
  };
};

export default useTaskList;
