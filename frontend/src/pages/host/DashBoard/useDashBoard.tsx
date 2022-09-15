import { useQuery } from 'react-query';
import { useParams } from 'react-router-dom';

import SlackUrlModal from '@/components/host/SlackUrlModal';

import useModal from '@/hooks/useModal';
import useToast from '@/hooks/useToast';

import apiHost from '@/apis/host';
import apiJobs from '@/apis/job';
import apiSpace from '@/apis/space';
import apiSubmission from '@/apis/submission';

import { ID } from '@/types';

const useDashBoard = () => {
  const { openModal } = useModal();
  const { openToast } = useToast();

  const { spaceId } = useParams() as { spaceId: ID };

  const { data: spaceData } = useQuery(['space', spaceId], () => apiSpace.getSpace(spaceId), {
    staleTime: 0,
    cacheTime: 0,
  });
  const { data: jobsData } = useQuery(['jobs', spaceId], () => apiJobs.getJobs(spaceId));
  const { data: submissionData } = useQuery(['submissions', spaceId], () => apiSubmission.getSubmission(spaceId));
  const { refetch: copyEntranceLink } = useQuery(['entranceCode'], () => apiHost.getEntranceCode(), {
    suspense: false,
    enabled: false,
    onSuccess: data => {
      navigator.clipboard
        .writeText(`${location.origin}/enter/${data.entranceCode}/pwd`)
        .then(() => {
          openToast('SUCCESS', '공간 입장 링크가 복사되었습니다.');
        })
        .catch(() => {
          openToast('ERROR', '다시 시도해주세요.');
        });
    },
    onError: () => {
      openToast('ERROR', '잠시 후 다시 시도해주세요.');
    },
  });

  const onClickSlackButton = () => {
    openModal(<SlackUrlModal jobs={jobsData?.jobs || []} />);
  };

  const onClickLinkButton = () => {
    copyEntranceLink();
  };

  return {
    spaceId,
    spaceData,
    jobsData,
    submissionData,
    onClickSlackButton,
    onClickLinkButton,
  };
};

export default useDashBoard;
