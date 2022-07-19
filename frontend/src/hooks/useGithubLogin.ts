import { useEffect } from 'react';
import { useQuery } from 'react-query';
import { useNavigate } from 'react-router-dom';

import apis from '@/apis';
import githubAuth from '@/apis/githubAuth';

const useGitHubLogin = () => {
  const code = new URL(location.href).searchParams.get('code');

  const navigate = useNavigate();

  const { data } = useQuery(['hostToken'], () => githubAuth.getToken(code), { suspense: true });
  const { data: spacesData } = useQuery(['spaces'], apis.getSpaces, { suspense: true });

  useEffect(() => {
    if (data) {
      localStorage.setItem('token', data.token);

      if (spacesData?.spaces.length === 0) {
        navigate('/host/manage/spaceCreate');
        return;
      }

      const space = spacesData?.spaces[0];
      navigate(`/host/manage/${space?.id}`);
    }
  }, [data, spacesData]);
};

export default useGitHubLogin;
