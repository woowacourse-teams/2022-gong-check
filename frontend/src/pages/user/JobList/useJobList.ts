import { useQuery } from 'react-query';
import { useParams } from 'react-router-dom';

import useGoPreviousPage from '@/hooks/useGoPreviousPage';

import apiJobs from '@/apis/job';
import apiSpace from '@/apis/space';

const useJobList = () => {
  const { spaceId } = useParams();

  const { goPreviousPage } = useGoPreviousPage();

  const { data: jobsData } = useQuery(['jobs', spaceId], () => apiJobs.getJobs(spaceId), {
    suspense: true,
    retry: false,
  });
  const { data: spaceData } = useQuery(['spaces', spaceId], () => apiSpace.getSpace(spaceId), {
    suspense: true,
    retry: false,
  });

  return { jobsData, spaceData, goPreviousPage };
};

export default useJobList;
