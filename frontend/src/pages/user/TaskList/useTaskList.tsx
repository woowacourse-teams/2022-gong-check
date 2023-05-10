import { useEffect, useState } from 'react';
import { useRef } from 'react';
import { useQuery } from 'react-query';
import { useLocation, useParams } from 'react-router-dom';

import DetailInfoModal from '@/components/user/DetailInfoModal';
import NameModal from '@/components/user/NameModal';

import useGoPreviousPage from '@/hooks/useGoPreviousPage';
import useModal from '@/hooks/useModal';
import useSectionCheck from '@/hooks/useSectionCheck';
import useSocket from '@/hooks/useSocket';
import useToast from '@/hooks/useToast';

import apis from '@/apis';

import { ID, SectionType } from '@/types';
import { ApiTaskData } from '@/types/apis';

const useTaskList = () => {
  const isSubmitted = useRef<boolean>(false);

  const { spaceId, jobId } = useParams() as { spaceId: ID; jobId: ID };

  const location = useLocation();
  const locationState = location.state as { jobName: string };

  const { checkIsConnected, connectSocket, disconnectSocket, subscribeTopic, sendMessage } = useSocket(
    `${process.env.REACT_APP_WS_URL}/ws-connect`
  );

  const { openModal, closeModal } = useModal();
  const { openToast } = useToast();

  const { goPreviousPage } = useGoPreviousPage();

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

  const { data: spaceData } = useQuery(['space', spaceId], () => apis.getSpace(spaceId));
  const { data: runningTasksData } = useQuery(['runningTask', jobId], () => apis.getRunningTasks(jobId));

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
        completeJobs={completeJobs}
      />
    );
  };

  const onClickSectionDetail = (section: SectionType) => {
    openModal(<DetailInfoModal name={section.name} imageUrl={section.imageUrl} description={section.description} />);
  };

  const onClickGoPreviousPage = () => {
    disconnectSocket();
    goPreviousPage();
  };

  const completeJobs = (author: string) => {
    isSubmitted.current = true;
    sendMessage(`/app/jobs/${jobId}/complete`, { author });
  };

  const flipTaskCheck = (taskId: ID) => {
    sendMessage(`/app/jobs/${jobId}/tasks/flip`, { taskId });
  };

  const onClickSectionAllCheck = (sectionId: ID) => {
    sendMessage(`/app/jobs/${jobId}/sections/checkAll`, { sectionId });
  };

  const onConnectSocket = () => {
    subscribeTopic(`/topic/jobs/${jobId}`, (data: any) => {
      setSectionsData(JSON.parse(data.body));
    });

    subscribeTopic(`/topic/jobs/${jobId}/complete`, () => {
      closeModal();
      goPreviousPage();

      isSubmitted.current
        ? openToast('SUCCESS', '체크리스트를 제출하였습니다.')
        : openToast('ERROR', '해당 체크리스트를 다른 사용자가 제출하였습니다.');
    });
  };

  const onErrorSocket = () => {
    openToast('ERROR', '연결에 문제가 있습니다.');
    disconnectSocket();
    goPreviousPage();
  };

  const onDisconnectSocket = () => {
    const isConnected = checkIsConnected();

    if (isConnected) {
      openToast('ERROR', '장시간 동작이 없어 이전 페이지로 이동합니다.');
      goPreviousPage();
    }
  };

  useEffect(() => {
    connectSocket(onConnectSocket, onErrorSocket, onDisconnectSocket);

    return () => {
      disconnectSocket();
    };
  }, []);

  useEffect(() => {
    if (runningTasksData) setSectionsData(runningTasksData);
  }, [runningTasksData]);

  return {
    spaceData,
    onSubmit,
    onClickGoPreviousPage,
    totalCount,
    checkedCount,
    percent,
    sectionsAllCheckMap,
    isAllChecked,
    locationState,
    sectionsData,
    onClickSectionDetail,
    onClickSectionAllCheck,
    flipTaskCheck,
  };
};

export default useTaskList;
