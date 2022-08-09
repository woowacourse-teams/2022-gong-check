import { useState } from 'react';
import { useQuery } from 'react-query';
import { useNavigate, useParams } from 'react-router-dom';

import apiSpace from '@/apis/space';

import { ID } from '@/types';

const useHostNavigation = () => {
  const navigate = useNavigate();

  const { spaceId } = useParams() as { spaceId: ID };

  const [selectedSpaceId, setSelectedSpaceId] = useState<ID>(spaceId);

  const { data: spaceData } = useQuery(['spaces'], apiSpace.getSpaces);

  const onClickPasswordUpdate = () => {
    navigate('/host/manage/passwordUpdate');
  };

  const onClickSpace = (spaceId: ID) => {
    setSelectedSpaceId(spaceId);
    navigate(`${spaceId}`);
  };

  const onClickNewSpace = () => {
    navigate('/host/manage/spaceCreate');
  };

  return { selectedSpaceId, spaceData, onClickPasswordUpdate, onClickSpace, onClickNewSpace };
};

export default useHostNavigation;
