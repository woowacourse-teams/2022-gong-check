import { AxiosError } from 'axios';
import { useMutation, useQuery } from 'react-query';
import { useNavigate } from 'react-router-dom';

import useToast from '@/hooks/useToast';

import apis from '@/apis';

import { ID } from '@/types';

import errorMessage from '@/constants/errorMessage';

const useJobCard = (jobName: string, jobId: ID) => {
  const navigate = useNavigate();

  const { openToast } = useToast();

  const { refetch: getJobActive } = useQuery(['jobActive', jobId], () => apis.getJobActive(jobId), {
    suspense: false,
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
      retry: false,
      onSuccess: () => navigate(jobId.toString(), { state: { jobName } }),
      onError: (err: AxiosError<{ errorCode: keyof typeof errorMessage }>) => {
        openToast('ERROR', errorMessage[`${err.response?.data.errorCode!}`]);
      },
    }
  );

  const onClickJobCard = () => {
    getJobActive();
  };

  return { onClickJobCard };
};

export default useJobCard;
