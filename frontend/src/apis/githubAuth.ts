import axios, { AxiosResponse } from 'axios';

const getToken = async (code: string | null) => {
  const {
    data,
  }: AxiosResponse<{
    token: string;
    existHost: boolean;
  }> = await axios({
    method: 'POST',
    url: `http://192.168.6.158:8080/api/login`,
    data: {
      code,
    },
  });

  return data;
};

const githubAuth = { getToken };

export default githubAuth;
