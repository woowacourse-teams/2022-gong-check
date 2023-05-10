import { AxiosError } from 'axios';
import React, { useEffect, useState } from 'react';
import { useMutation } from 'react-query';
import { useNavigate, useParams } from 'react-router-dom';

import useSections from '@/hooks/useSections';
import useToast from '@/hooks/useToast';

import apiJobs from '@/apis/job';

import { ID, SectionType } from '@/types';

import errorMessage from '@/constants/errorMessage';

type MutationParams = { spaceId: ID; newJobName: string; sections: SectionType[] };

const useJobCreate = () => {
  const navigate = useNavigate();

  const { spaceId } = useParams() as { spaceId: ID };

  const [newJobName, setNewJobName] = useState('');

  const { sections, createSection, resetSections } = useSections();

  const { openToast } = useToast();

  const { mutate: createNewJob } = useMutation(
    ({ spaceId, newJobName, sections }: MutationParams) => apiJobs.postNewJob(spaceId, newJobName, sections),
    {
      onSuccess: () => {
        openToast('SUCCESS', '업무가 생성 되었습니다.');
        navigate(`/host/manage/${spaceId}`);
      },
      onError: (err: AxiosError<{ errorCode: keyof typeof errorMessage }>) => {
        openToast('ERROR', errorMessage[`${err.response?.data.errorCode!}`]);
      },
    }
  );

  const onChangeJobName = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNewJobName(e.target.value);
  };

  const onClickCreateNewJob = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    createNewJob({ spaceId, newJobName, sections });
  };

  useEffect(() => {
    return () => resetSections();
  }, []);

  return { sections, createSection, newJobName, onChangeJobName, onClickCreateNewJob };
};

export default useJobCreate;
