import { useEffect } from 'react';
import { useQuery } from 'react-query';

import apiAuth from '@/apis/githubAuth';

const useGitHubLogin = () => {
  const code = new URL(location.href).searchParams.get('code') || '';

  const { isSuccess: isSuccessGithubLogin, data: tokenData } = useQuery(['hostToken'], () => apiAuth.getToken(code));

  useEffect(() => {
    if (tokenData) {
      localStorage.setItem('host', tokenData.token);
    }
  }, [tokenData]);

  return { isSuccessGithubLogin };
};

export default useGitHubLogin;
