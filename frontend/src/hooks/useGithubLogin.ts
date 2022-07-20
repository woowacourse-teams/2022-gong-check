import { useEffect } from 'react';
import { useQuery } from 'react-query';

import apiAuth from '@/apis/githubAuth';

const useGitHubLogin = () => {
  const code = new URL(location.href).searchParams.get('code');

  const { isSuccess: isSuccessGithubLogin, data } = useQuery(['hostToken'], () => apiAuth.getToken(code), {
    suspense: true,
  });

  useEffect(() => {
    if (data) {
      localStorage.setItem('token', data.token);
    }
  }, [data]);

  return { isSuccessGithubLogin };
};

export default useGitHubLogin;
