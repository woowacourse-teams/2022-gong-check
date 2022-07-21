import { useQuery } from 'react-query';
import { useNavigate, useParams } from 'react-router-dom';

import SlackUrlModal from '@/components/host/SlackUrlModal';

import useModal from '@/hooks/useModal';

import apiJobs from '@/apis/job';
import apiSpace from '@/apis/space';
import apiSubmission from '@/apis/submission';

const useDashBoard = () => {
  const navigate = useNavigate();

  const { spaceId } = useParams();

  const { openModal } = useModal();

  const { data: spaceData } = useQuery(['space', spaceId], () => apiSpace.getSpace({ spaceId }), { suspense: true });
  const { data: jobsData } = useQuery(['jobs', spaceId], () => apiJobs.getJobs(spaceId), { suspense: true });
  const { data: submissionData } = useQuery(['submissions', spaceId], () => apiSubmission.getSubmission({ spaceId }), {
    suspense: true,
  });

  const onClickSubmissionsDetail = () => {
    navigate('spaceRecord');
  };

  const onClickSlackButton = () => {
    openModal(<SlackUrlModal jobs={jobsData?.jobs || []} />);
  };

  const onClickLinkButton = () => {
    alert('공간 입장 링크가 복사되었습니다.');
  };

  return {
    spaceData,
    jobsData,
    submissionData,
    onClickSubmissionsDetail,
    onClickSlackButton,
    onClickLinkButton,
  };
};

export default useDashBoard;
