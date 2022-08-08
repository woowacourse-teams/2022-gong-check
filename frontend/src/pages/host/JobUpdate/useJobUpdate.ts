import React, { useEffect, useState } from 'react';
import { useMutation, useQuery } from 'react-query';
import { useLocation, useNavigate, useParams } from 'react-router-dom';

import useSections from '@/hooks/useSections';
import useToast from '@/hooks/useToast';

import apiJobs from '@/apis/job';
import apiTask from '@/apis/task';

import { SectionType } from '@/types';
import { ApiError } from '@/types/apis';

type MutationParams = { jobId: string | number | undefined; jobName: string; sections: SectionType[] };

const useJobUpdate = () => {
  const navigate = useNavigate();

  const { spaceId, jobId } = useParams();

  const location = useLocation();
  const state = location.state as { jobName: string };

  const [jobName, setJobName] = useState('');

  const { sections, createSection, updateSection, resetSections } = useSections();

  const { openToast } = useToast();

  const { refetch: getTaskData } = useQuery(['taskData', jobId], () => apiTask.getTasks(jobId), {
    enabled: false,
    staleTime: Infinity,
    onSuccess: data => {
      updateSection(data.sections);
    },
    onError: (err: ApiError) => {
      openToast('ERROR', `${err.response?.data.message}`);
    },
  });

  const { mutate: updateJob } = useMutation(
    ({ jobId, jobName, sections }: MutationParams) => apiJobs.putJob(jobId, jobName, sections),
    {
      onSuccess: () => {
        openToast('SUCCESS', '업무 수정에 성공하였습니다.');
        navigate(`/host/manage/${spaceId}`);
      },
      onError: (err: ApiError) => {
        openToast('ERROR', `${err.response?.data.message}`);
      },
    }
  );

  const onChangeJobName = (e: React.ChangeEvent<HTMLInputElement>) => {
    setJobName(e.target.value);
  };

  const onClickUpdateJob = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    updateJob({ jobId, jobName, sections });
    resetSections();
  };

  useEffect(() => {
    setJobName(state.jobName);
    getTaskData();
  }, []);

  return { sections, createSection, jobName, onChangeJobName, onClickUpdateJob };
};

export default useJobUpdate;
