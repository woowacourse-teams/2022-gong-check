import { axiosInstanceToken } from '../config';
import { AxiosResponse } from 'axios';

import { ApiSubmissionData } from '@/types/apis';

const postJobComplete = ({ jobId, author }: any) => {
  return axiosInstanceToken({
    method: 'POST',
    url: `/api/jobs/${jobId}/complete`,
    data: {
      author,
    },
  });
};

// Sprint 2------

// submission 목록 조회
const getSubmission = async ({ spaceId }: any) => {
  const { data }: AxiosResponse<ApiSubmissionData> = await axiosInstanceToken({
    method: 'GET',
    url: `/api/spaces/${spaceId}/submissions`,
  });

  return data;
};

const apis = { postJobComplete, getSubmission };

export default apis;
