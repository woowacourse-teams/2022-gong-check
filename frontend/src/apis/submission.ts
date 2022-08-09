import { AxiosResponse } from 'axios';

import { ID } from '@/types';
import { ApiSubmissionData } from '@/types/apis';

import { axiosInstanceToken } from './config';

const postJobComplete = (jobId: ID, author: string) => {
  return axiosInstanceToken({
    method: 'POST',
    url: `/api/jobs/${jobId}/complete`,
    data: {
      author,
    },
  });
};

const getSubmission = async (spaceId: ID) => {
  const { data }: AxiosResponse<ApiSubmissionData> = await axiosInstanceToken({
    method: 'GET',
    url: `/api/spaces/${spaceId}/submissions`,
  });

  return data;
};

const apiSubmission = { postJobComplete, getSubmission };

export default apiSubmission;
