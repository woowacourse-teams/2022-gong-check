import { useQuery } from 'react-query';
import { useParams } from 'react-router-dom';

import useGoPreviousPage from '@/hooks/useGoPreviousPage';

import apiJobs from '@/apis/job';
import apiSpace from '@/apis/space';

import { ID } from '@/types';

const useJobList = () => {
  const { spaceId } = useParams() as { spaceId: ID };

  const { goPreviousPage } = useGoPreviousPage();

  const { data: jobsData } = useQuery(['jobs', spaceId], () => apiJobs.getJobs(spaceId));
  const { data: spaceData } = useQuery(['spaces', spaceId], () => apiSpace.getSpace(spaceId));

  return { jobsData, spaceData, goPreviousPage };
};

export default useJobList;
