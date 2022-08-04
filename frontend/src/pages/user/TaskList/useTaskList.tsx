import { useQuery } from 'react-query';
import { useLocation, useNavigate, useParams } from 'react-router-dom';

import DetailedInfoCardModal from '@/components/user/DetailedInfoCardModal';
import NameModal from '@/components/user/NameModal';

import useGoPreviousPage from '@/hooks/useGoPreviousPage';
import useModal from '@/hooks/useModal';
import useToast from '@/hooks/useToast';

import apis from '@/apis';

import { ApiError } from '@/types/apis';

const RE_FETCH_INTERVAL_TIME = 1000;

const useTaskList = () => {
  const { spaceId, jobId, hostId } = useParams();
  const location = useLocation();
  const navigate = useNavigate();
  const locationState = location.state as { jobName: string } | undefined;

  const { openModal } = useModal();
  const { openToast } = useToast();

  const { goPreviousPage } = useGoPreviousPage();

  const { data: sectionsData, refetch: getSections } = useQuery(
    ['sections', jobId],
    () => apis.getRunningTasks(jobId),
    {
      suspense: true,
      retry: false,
      refetchInterval: RE_FETCH_INTERVAL_TIME,
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

  return {
    locationState,
    sectionsData,
    spaceData,
    getSections,
    onClickButton,
    goPreviousPage,
    onClickSectionDetail,
  };
};

export default useTaskList;
