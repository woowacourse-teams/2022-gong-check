import { useEffect, useMemo, useRef, useState } from 'react';
import { useQuery } from 'react-query';
import { useLocation, useParams } from 'react-router-dom';

import DetailedInfoCardModal from '@/components/user/DetailedInfoCardModal';
import NameModal from '@/components/user/NameModal';

import useGoPreviousPage from '@/hooks/useGoPreviousPage';
import useModal from '@/hooks/useModal';

import apis from '@/apis';

const RE_FETCH_INTERVAL_TIME = 100;
const PROGRESS_BAR_DEFAULT_POSITION = 232;

const useTaskList = () => {
  const { spaceId, jobId, hostId } = useParams();

  const location = useLocation();
  const locationState = location.state as { jobName: string } | undefined;

  const { openModal } = useModal();

  const progressBarRef = useRef<HTMLDivElement>(null);

  const { goPreviousPage } = useGoPreviousPage();

  const [isSticked, setIsSticked] = useState(false);

  const { data: sectionsData, refetch: getSections } = useQuery(
    ['sections', jobId],
    () => apis.getRunningTasks(jobId),
    {
      refetchInterval: RE_FETCH_INTERVAL_TIME,
      cacheTime: 0,
    }
  );

  const { data: spaceData } = useQuery(['space', jobId], () => apis.getSpace(spaceId));

  const onClickButton = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
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

  const onClickSectionDetail = ({
    name,
    imageUrl,
    description,
  }: {
    name: string;
    imageUrl: string;
    description: string;
  }) => {
    openModal(<DetailedInfoCardModal name={name} imageUrl={imageUrl} description={description} />);
  };

  if (!sectionsData?.sections.length) return { isNotData: true };

  const { sections } = sectionsData;

  const tasks = sections.map(section => section.tasks.map(task => task.checked));
  const checkList = tasks.reduce((prev, cur) => {
    return prev.concat(...cur);
  });

  const totalCount = useMemo(() => checkList.length, [checkList]);
  const checkCount = useMemo(() => checkList.filter(check => check === true).length, [checkList]);
  const percent = useMemo(() => Math.ceil((checkCount / totalCount) * 100), [checkCount, totalCount]);
  const isAllChecked = totalCount === checkCount;

  useEffect(() => {
    const isStartSticked = progressBarRef.current?.offsetTop! > PROGRESS_BAR_DEFAULT_POSITION;

    setIsSticked(isStartSticked);
  }, [progressBarRef.current?.offsetTop]);

  return {
    spaceData,
    getSections,
    onClickButton,
    goPreviousPage,
    totalCount,
    checkCount,
    percent,
    isAllChecked,
    locationState,
    sectionsData,
    onClickSectionDetail,
    progressBarRef,
    isSticked,
  };
};

export default useTaskList;
