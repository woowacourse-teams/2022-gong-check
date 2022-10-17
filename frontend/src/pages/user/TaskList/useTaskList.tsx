import { Stomp } from '@stomp/stompjs';
import { useEffect, useState } from 'react';
import { useRef } from 'react';
import { useMutation, useQuery } from 'react-query';
import { useLocation, useParams } from 'react-router-dom';
import SockJS from 'sockjs-client';

import DetailInfoModal from '@/components/user/DetailInfoModal';
import NameModal from '@/components/user/NameModal';

import useGoPreviousPage from '@/hooks/useGoPreviousPage';
import useModal from '@/hooks/useModal';
import useSectionCheck from '@/hooks/useSectionCheck';

import apis from '@/apis';

import { ID, SectionType } from '@/types';
import { ApiTaskData } from '@/types/apis';

const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';

const useTaskList = () => {
  const { spaceId, jobId } = useParams() as { spaceId: ID; jobId: ID };

  const location = useLocation();
  const locationState = location.state as { jobName: string };

  const { openModal } = useModal();

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

  const { mutate: postSectionAllCheck } = useMutation((sectionId: ID) => apis.postSectionAllCheckTask(sectionId));

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
        jobId={jobId}
      />
    );
  };

  const onClickSectionDetail = (section: SectionType) => {
    openModal(<DetailInfoModal name={section.name} imageUrl={section.imageUrl} description={section.description} />);
  };

  const onClickSectionAllCheck = (sectionId: ID) => {
    postSectionAllCheck(sectionId);
  };

  const flipTaskCheck = (taskId: ID) => {
    if (!stomp.current) return;
    stomp.current.send(`/app/jobs/${jobId}/tasks/flip`, {}, JSON.stringify({ taskId }));
  };

  useEffect(() => {
    const sock = new SockJS(`${API_URL}/ws/connect`);
    stomp.current = Stomp.over(sock);

    stomp.current.connect({}, () => {
      stomp.current.subscribe(`/topic/${jobId}`, (data: any) => {
        setSectionsData(JSON.parse(data.body));
      });
    });

    return () => {
      stomp.current.disconnect();
      sock.close();
    };

    // sse.addEventListener('submit', () => {
    //   closeModal();
    //   openToast('SUCCESS', '해당 체크리스트는 제출되었습니다.');
    //   goPreviousPage();
    // });
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
