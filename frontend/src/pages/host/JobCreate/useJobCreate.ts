import { useState } from 'react';
import { useMutation } from 'react-query';
import { useNavigate, useParams } from 'react-router-dom';

import useToast from '@/hooks/useToast';

import apiJobs from '@/apis/job';

import { SectionType } from '@/types';
import { ApiError } from '@/types/apis';

type MutationParams = { spaceId: string | number | undefined; newJobName: string; sections: SectionType[] };

const useJobCreate = (sections: SectionType[]) => {
  const navigate = useNavigate();
  const { openToast } = useToast();

  const { spaceId } = useParams();

  const [newJobName, setNewJobName] = useState('새 업무');

  const { mutate: createNewJob } = useMutation(
    ({ spaceId, newJobName, sections }: MutationParams) => apiJobs.postNewJob(spaceId, newJobName, sections),
    {
      onSuccess: () => {
        openToast('SUCCESS', '업무가 생성 되었습니다.');
        navigate(`/host/manage/${spaceId}`);
      },
      onError: (err: ApiError) => {
        openToast('ERROR', `${err.response?.data.message}`);
        navigate('/host/manage/spaceCreate');
      },
    }
  );

  const onChangeJobName = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNewJobName(e.target.value);
  };

  const onClickCreateNewJob = () => {
    createNewJob({ spaceId, newJobName, sections });
  };

  return { newJobName, onChangeJobName, onClickCreateNewJob };
};

export default useJobCreate;
