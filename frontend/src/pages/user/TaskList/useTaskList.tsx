import { useMemo } from 'react';
import { useQuery } from 'react-query';
import { useParams } from 'react-router-dom';

import NameModal from '@/components/user/NameModal';

import useGoPreviousPage from '@/hooks/useGoPreviousPage';
import useModal from '@/hooks/useModal';

import apis from '@/apis';

const useTaskList = () => {
  const { spaceId, jobId, hostId } = useParams();

  const { openModal } = useModal();

  const { goPreviousPage } = useGoPreviousPage();

  const { data: sectionsData, refetch: getSections } = useQuery(
    ['sections', jobId],
    () => apis.getRunningTasks(jobId),
    {
      suspense: true,
      retry: false,
    }
  );

  const { data: spaceData } = useQuery(['space', jobId], () => apis.getSpace(spaceId), {
    suspense: true,
    retry: false,
  });

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

  return {
    sectionsData,
    spaceData,
    getSections,
    onClickButton,
    goPreviousPage,
  };
};

export default useTaskList;
