import { AxiosResponse } from 'axios';

import { SectionType } from '@/types';
import { ApiJobActiveData, ApiJobData } from '@/types/apis';

import { axiosInstanceToken } from './config';

const getJobs = async (spaceId: number | string | undefined) => {
  const { data }: AxiosResponse<ApiJobData> = await axiosInstanceToken({
    method: 'GET',
    url: `/api/spaces/${spaceId}/jobs`,
  });

  return data;
};

const getJobActive = async ({ jobId }: any) => {
  const { data }: AxiosResponse<ApiJobActiveData> = await axiosInstanceToken({
    method: 'GET',
    url: `/api/jobs/${jobId}/active`,
  });

  return data;
};

// Sprint 2-----

// job 생성
// Location : /api/jobs/{jobId}
const postNewJob = (spaceId: number | string | undefined, name: string, sections: SectionType[]) => {
  return axiosInstanceToken({
    method: 'POST',
    url: `/api/spaces/${spaceId}/jobs`,
    data: {
      name,
      sections,
    },
  });
};

// job 수정
const putJob = ({ jobId, name, sections }: any) => {
  return axiosInstanceToken({
    method: 'PUT',
    url: `/api/jobs/${jobId}`,
    data: {
      name,
      sections,
    },
  });
};

// job 삭제
const deleteJob = ({ jobId }: any) => {
  return axiosInstanceToken({
    method: 'DELETE',
    url: `/api/jobs/${jobId}`,
  });
};

const apiJobs = { getJobs, getJobActive, postNewJob, putJob, deleteJob };

export default apiJobs;
