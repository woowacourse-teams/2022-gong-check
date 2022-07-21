import { useState } from 'react';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';

import apiJobs from '@/apis/job';

import { ID } from '@/types';

const useJobBox = () => {
  const navigate = useNavigate();

  const { mutate: deleteJob } = useMutation((jobId: ID) => apiJobs.deleteJob(jobId));

  const [isClicked, setIsClicked] = useState(false);

  const onClickUpdateJobButton = (jobId: ID, jobName: string) => {
    navigate(`jobUpdate/${jobId}`, { state: { jobName } });
  };

  const onClickDeleteJobButton = (jobId: ID) => {
    if (confirm('해당 업무를 삭제하시겠습니까?')) {
      deleteJob(jobId);
    }
  };

  const onClickJobBox = () => {
    setIsClicked(prev => !prev);
  };

  return { isClicked, onClickUpdateJobButton, onClickDeleteJobButton, onClickJobBox };
};

export default useJobBox;
