import { useQuery } from 'react-query';
import { useNavigate, useParams } from 'react-router-dom';

import apiJobs from '@/apis/job';
import apiSpace from '@/apis/space';
import apiSubmission from '@/apis/submission';

const useDashBoard = () => {
  const navigate = useNavigate();

  const { spaceId } = useParams();

  const { data: spaceData } = useQuery(['space', spaceId], () => apiSpace.getSpace({ spaceId }), { suspense: true });
  const { data: jobsData } = useQuery(['jobs', spaceId], () => apiJobs.getJobs(spaceId), { suspense: true });
  const { data: submissionData } = useQuery(['submissions', spaceId], () => apiSubmission.getSubmission({ spaceId }), {
    suspense: true,
  });

  const onClickSubmissionsDetail = () => {
    navigate('spaceRecord');
  };

  return {
    spaceData,
    jobsData,
    submissionData,
    onClickSubmissionsDetail,
  };
};

export default useDashBoard;
