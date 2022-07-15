import { useEffect } from 'react';
import { useQuery } from 'react-query';
import { useNavigate } from 'react-router-dom';

import githubAuth from '@/apis/githubAuth';

const useGitHubLogin = () => {
  const code = new URL(location.href).searchParams.get('code');

  const navigate = useNavigate();

  const { data } = useQuery(['hostToken'], () => githubAuth.getToken(code), { suspense: true });

  useEffect(() => {
    if (data) {
      localStorage.setItem('host', data.token);

      data.existHost ? navigate('/host') : navigate('/enter/1');
    }
  }, [data]);
};

export default useGitHubLogin;
