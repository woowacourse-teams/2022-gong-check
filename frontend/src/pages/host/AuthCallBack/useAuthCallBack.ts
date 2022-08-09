import { useEffect } from 'react';
import { useQuery } from 'react-query';
import { useNavigate } from 'react-router-dom';

import useGitHubLogin from '@/hooks/useGithubLogin';

import apis from '@/apis';

const useAuthCallBack = () => {
  const navigate = useNavigate();

  const { isSuccessGithubLogin } = useGitHubLogin();

  const { data: spacesData, refetch: getSpaceData } = useQuery(['spaces'], apis.getSpaces, {
    enabled: false,
  });

  useEffect(() => {
    if (isSuccessGithubLogin) {
      getSpaceData();
    }
  }, [isSuccessGithubLogin]);

  useEffect(() => {
    if (!spacesData) return;

    if (spacesData.spaces.length === 0) {
      navigate('/host/manage/spaceCreate');
      return;
    }

    const firstSpace = spacesData.spaces[0];
    navigate(`/host/manage/${firstSpace.id}`);
  }, [spacesData]);
};

export default useAuthCallBack;
