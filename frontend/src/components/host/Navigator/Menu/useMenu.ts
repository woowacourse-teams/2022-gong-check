import { useState } from 'react';
import { useQuery } from 'react-query';
import { useNavigate, useParams } from 'react-router-dom';

import apiHost from '@/apis/host';
import apiSpace from '@/apis/space';

import { ID } from '@/types';

const useHostNavigator = () => {
  const navigate = useNavigate();

  const { spaceId } = useParams() as { spaceId: ID };

  const [selectedSpaceId, setSelectedSpaceId] = useState<ID>(spaceId);

  const { data: spaceData } = useQuery(['spaces'], apiSpace.getSpaces, {
    suspense: false,
  });

  const { data: entranceCodeData } = useQuery(['entranceCode'], () => apiHost.getEntranceCode(), {
    staleTime: Infinity,
    cacheTime: Infinity,
  });

  const hostId = entranceCodeData?.entranceCode!;

  const onClickUpdate = () => {
    navigate('/host/manage/update');
  };

  const onClickSpace = (spaceId: ID) => {
    setSelectedSpaceId(spaceId);
    navigate(`${spaceId}`);
  };

  const onClickNewSpace = () => {
    navigate('/host/manage/spaceCreate');
  };

  const onClickLogout = () => {
    if (confirm('로그아웃하시겠습니까?')) {
      sessionStorage.removeItem('host');
      localStorage.removeItem('host');
      navigate('/host');
    }
  };

  return { selectedSpaceId, spaceData, onClickUpdate, onClickSpace, onClickNewSpace, onClickLogout, hostId };
};

export default useHostNavigator;
