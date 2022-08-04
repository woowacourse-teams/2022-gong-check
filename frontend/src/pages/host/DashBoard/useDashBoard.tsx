import { clip } from '@/utils/copy';
import { useQuery } from 'react-query';
import { useNavigate, useParams } from 'react-router-dom';

import SlackUrlModal from '@/components/host/SlackUrlModal';

import useModal from '@/hooks/useModal';
import useToast from '@/hooks/useToast';

import apiHost from '@/apis/host';
import apiJobs from '@/apis/job';
import apiSpace from '@/apis/space';
import apiSubmission from '@/apis/submission';

const useDashBoard = () => {
  const navigate = useNavigate();

  const { spaceId } = useParams();

  const { openModal } = useModal();
  const { openToast } = useToast();

  const { data: spaceData } = useQuery(['space', spaceId], () => apiSpace.getSpace(spaceId), {
    suspense: true,
    staleTime: 0,
    cacheTime: 0,
  });
  const { data: jobsData } = useQuery(['jobs', spaceId], () => apiJobs.getJobs(spaceId), { suspense: true });
  const { data: submissionData } = useQuery(['submissions', spaceId], () => apiSubmission.getSubmission({ spaceId }), {
    suspense: true,
  });
  const { refetch: getEntranceCode } = useQuery(['entranceCode'], () => apiHost.getEntranceCode(), {
    retry: false,
    enabled: false,
    onSuccess: data => {
      clip(`${location.origin}/enter/${data.entranceCode}/pwd`);
      openToast('SUCCESS', '공간 입장 링크가 복사되었습니다.');
    },
    onError: () => {
      openToast('ERROR', '잠시 후 다시 시도해주세요.');
    },
  });

  const onClickSubmissionsDetail = () => {
    navigate('spaceRecord');
  };

  const onClickSlackButton = () => {
    openModal(<SlackUrlModal jobs={jobsData?.jobs || []} />);
  };

  const onClickLinkButton = () => {
    getEntranceCode();
  };

  return {
    spaceId,
    spaceData,
    jobsData,
    submissionData,
    onClickSubmissionsDetail,
    onClickSlackButton,
    onClickLinkButton,
  };
};

export default useDashBoard;
