import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import { SpaceType } from '@/types';

const useSpaceInfo = (space?: SpaceType, type?: 'read' | 'create' | 'update') => {
  const navigate = useNavigate();
  const [name, setName] = useState<string>('');

  const [isActiveSubmit, setIsActiveSubmit] = useState(type === 'update');

  const onChangeSpaceName = (e: React.ChangeEvent<HTMLInputElement>) => {
    const input = e.target as HTMLInputElement;

    const isExistValue = input.value.length > 0;
    setIsActiveSubmit(isExistValue);
  };

  const onClickEditSpaceInfo = () => {
    navigate(`/host/manage/${space?.id}/spaceUpdate`);
  };

  useEffect(() => {
    if (!space) return;

    setName(space.name);
  }, [space]);

  return { name, isActiveSubmit, onChangeSpaceName, onClickEditSpaceInfo };
};

export default useSpaceInfo;
