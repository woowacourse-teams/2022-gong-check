import { useMutation, useQuery } from 'react-query';
import { useNavigate } from 'react-router-dom';

import useToast from '@/hooks/useToast';

import apis from '@/apis';

import { ID } from '@/types';
import { ApiError } from '@/types/apis';

const useJobCard = (jobName: string, jobId: ID) => {
  const navigate = useNavigate();

  const { openToast } = useToast();

  const { refetch: getJobActive } = useQuery(['jobActive', jobId], () => apis.getJobActive(jobId), {
    enabled: false,
    onSuccess: data => {
      if (data.active) {
        navigate(jobId.toString(), { state: { jobName } });
        return;
      }
      if (confirm('진행중인 체크리스트가 없습니다. 새롭게 생성하시겠습니까?')) createNewRunningTask();
    },
  });

  const { mutate: createNewRunningTask } = useMutation(
    ['newRunningTask', jobId],
    () => apis.postNewRunningTasks(jobId),
    {
      onSuccess: () => navigate(jobId.toString(), { state: { jobName } }),
      onError: (err: ApiError) => {
        openToast('ERROR', `${err.response?.data.message}`);
      },
    }
  );

  const onClickJobCard = () => {
    getJobActive();
  };

  return { onClickJobCard };
};

export default useJobCard;
