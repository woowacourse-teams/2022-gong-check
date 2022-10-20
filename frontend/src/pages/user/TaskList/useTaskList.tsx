import { Stomp } from '@stomp/stompjs';
import { useEffect, useState } from 'react';
import { useRef } from 'react';
import { useQuery } from 'react-query';
import { useLocation, useParams } from 'react-router-dom';

import DetailInfoModal from '@/components/user/DetailInfoModal';
import NameModal from '@/components/user/NameModal';

import useGoPreviousPage from '@/hooks/useGoPreviousPage';
import useModal from '@/hooks/useModal';
import useSectionCheck from '@/hooks/useSectionCheck';
import useToast from '@/hooks/useToast';

import apis from '@/apis';

import { ID, SectionType } from '@/types';
import { ApiTaskData } from '@/types/apis';

const useTaskList = () => {
  const { spaceId, jobId } = useParams() as { spaceId: ID; jobId: ID };

  const location = useLocation();
  const locationState = location.state as { jobName: string };

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

  const stomp = useRef<any>(null);
  const isConnected = useRef<boolean>(false);
  const isSubmitted = useRef<boolean>(false);

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
    isConnected.current = false;
    goPreviousPage();
  };

  const completeJobs = (author: string) => {
    if (!stomp.current) return;
    isSubmitted.current = true;
    stomp.current.send(`/app/jobs/${jobId}/complete`, {}, JSON.stringify({ author }));
  };

  const flipTaskCheck = (taskId: ID) => {
    if (!stomp.current) return;
    stomp.current.send(`/app/jobs/${jobId}/tasks/flip`, {}, JSON.stringify({ taskId }));
  };

  const onClickSectionAllCheck = (sectionId: ID) => {
    if (!stomp.current) return;
    stomp.current.send(`/app/jobs/${jobId}/sections/checkAll`, {}, JSON.stringify({ sectionId }));
  };

  const connectSocket = () => {
    stomp.current = Stomp.client(`${process.env.REACT_APP_WS_URL}/ws-connect`);
    isConnected.current = true;

    stomp.current.reconnect_delay = 1000;
    stomp.current.heartbeat.outgoing = 600000;
    stomp.current.heartbeat.incoming = 600000;
    stomp.current.debug = () => {};

    stomp.current.reconnect_delay = 1000;
    stomp.current.heartbeat.outgoing = 600000;
    stomp.current.heartbeat.incoming = 600000;
    stomp.current.debug = () => {};

    stomp.current.connect(
      {},
      () => {
        stomp.current.subscribe(`/topic/jobs/${jobId}`, (data: any) => {
          setSectionsData(JSON.parse(data.body));
        });

        stomp.current.subscribe(`/topic/jobs/${jobId}/complete`, (data: any) => {
          closeModal();
          goPreviousPage();

          isSubmitted.current
            ? openToast('SUCCESS', '체크리스트를 제출하였습니다.')
            : openToast('ERROR', '해당 체크리스트를 다른 사용자가 제출하였습니다.');
        });
      },
      () => {
        openToast('ERROR', '연결에 문제가 있습니다.');
        goPreviousPage();
      }
    );
  };

  useEffect(() => {
    connectSocket();

    stomp.current.onDisconnect = () => {
      if (isConnected.current) {
        alert('이전 페이지로 이동하기');
        openToast('ERROR', '연결에 문제가 있어 이전 페이지로 이동합니다.');
        goPreviousPage();
      }
    };

    return () => {
      stomp.current.disconnect();
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
