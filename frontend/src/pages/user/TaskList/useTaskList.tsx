import { Stomp } from '@stomp/stompjs';
import { useEffect, useState } from 'react';
import { useRef } from 'react';
import { useQuery } from 'react-query';
import { useLocation, useParams } from 'react-router-dom';
import SockJS from 'sockjs-client';

import DetailInfoModal from '@/components/user/DetailInfoModal';
import NameModal from '@/components/user/NameModal';

import useGoPreviousPage from '@/hooks/useGoPreviousPage';
import useModal from '@/hooks/useModal';
import useSectionCheck from '@/hooks/useSectionCheck';
import useToast from '@/hooks/useToast';

import apis from '@/apis';

import { ID, SectionType } from '@/types';
import { ApiTaskData } from '@/types/apis';

const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';

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

  const { data: spaceData } = useQuery(['space', jobId], () => apis.getSpace(spaceId));
  const { data: runningTasksData } = useQuery(['runningTask'], () => apis.getRunningTasks(jobId));

  const { sectionsAllCheckMap, totalCount, checkedCount, percent, isAllChecked } = useSectionCheck(
    sectionsData?.sections || []
  );

  const stomp = useRef<any>(null);

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

  const completeJobs = (author: string) => {
    if (!stomp.current) return;
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

  useEffect(() => {
    const sock = new SockJS(`${API_URL}/ws-connect`);
    stomp.current = Stomp.over(sock);

    stomp.current.connect({}, () => {
      stomp.current.subscribe(`/topic/jobs/${jobId}`, (data: any) => {
        setSectionsData(JSON.parse(data.body));
      });

      stomp.current.subscribe(`/topic/jobs/${jobId}/complete`, (data: any) => {
        closeModal();
        openToast('SUCCESS', '해당 체크리스트는 제출되었습니다.');
        goPreviousPage();
      });
    });

    return () => {
      stomp.current.disconnect();
      sock.close();
    };
  }, []);

  useEffect(() => {
    if (runningTasksData) setSectionsData(runningTasksData);
  }, [runningTasksData]);

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
    flipTaskCheck,
  };
};

export default useTaskList;
