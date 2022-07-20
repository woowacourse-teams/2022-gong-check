import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';

import SlackUrlModal from '@/components/host/SlackUrlModal';

import useModal from '@/hooks/useModal';

import apiJobs from '@/apis/job';

import { ID, JobType } from '@/types';

const useJobListCard = (jobs: JobType[]) => {
  const navigate = useNavigate();

  const { openModal } = useModal();

  const { mutate: deleteJob } = useMutation((jobId: ID) => apiJobs.deleteJob(jobId));

  const onClickSlackButton = () => {
    openModal(<SlackUrlModal jobs={jobs} />);
  };

  const onClickNewJobButton = () => {
    navigate('jobCreate');
  };

  const onClickUpdateJobButton = () => {
    navigate('jobCreate');
  };

  const onClickDeleteJobButton = (jobId: ID) => {
    if (confirm('해당 업무를 삭제하시겠습니까?')) {
      deleteJob(jobId);
    }
  };

  return { onClickSlackButton, onClickNewJobButton, onClickUpdateJobButton, onClickDeleteJobButton };
};

export default useJobListCard;
