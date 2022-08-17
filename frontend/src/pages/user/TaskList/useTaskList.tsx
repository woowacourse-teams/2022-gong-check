import { EventSourcePolyfill } from 'event-source-polyfill';
import { useEffect, useRef, useState } from 'react';
import { useMutation, useQuery } from 'react-query';
import { useLocation, useNavigate, useParams } from 'react-router-dom';

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
const PROGRESS_BAR_DEFAULT_POSITION = 232;

const useTaskList = () => {
  const navigate = useNavigate();

  const { spaceId, jobId, hostId } = useParams() as { spaceId: ID; jobId: ID; hostId: ID };

  const location = useLocation();
  const locationState = location.state as { jobName: string };

  const { openModal } = useModal();
  const { openToast } = useToast();

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

  const { mutate: postSectionAllCheck } = useMutation((sectionId: ID) => apis.postSectionAllCheckTask(sectionId));

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
    const tokenKey = sessionStorage.getItem('tokenKey');
    if (!tokenKey) return;

    const sse = new EventSourcePolyfill(`${API_URL}/api/jobs/${jobId}/runningTasks/connect`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem(tokenKey)}`,
      },
    });

    sse.addEventListener('connect', (e: any) => {
      const { data: receivedSections } = e;

      setSectionsData(JSON.parse(receivedSections));
    });

    sse.addEventListener('flip', (e: any) => {
      const { data: receivedSections } = e;

      setSectionsData(JSON.parse(receivedSections));
    });

    sse.addEventListener('submit', () => {
      navigate(`/enter/${hostId}/spaces/${jobId}`);
      openToast('SUCCESS', '해당 체크리스트는 제출되었습니다.');
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
