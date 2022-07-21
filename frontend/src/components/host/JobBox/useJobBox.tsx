import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';

import apiJobs from '@/apis/job';

import { ID } from '@/types';

const useJobBox = () => {
  const navigate = useNavigate();

  const { mutate: deleteJob } = useMutation((jobId: ID) => apiJobs.deleteJob(jobId));

  const onClickUpdateJobButton = (jobId: ID) => {
    navigate(`jobUpdate/${jobId}`);
  };

  const onClickDeleteJobButton = (jobId: ID) => {
    if (confirm('해당 업무를 삭제하시겠습니까?')) {
      deleteJob(jobId);
    }
  };

  return { onClickUpdateJobButton, onClickDeleteJobButton };
};

export default useJobBox;
