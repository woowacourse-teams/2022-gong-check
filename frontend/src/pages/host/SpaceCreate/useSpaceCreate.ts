import { useState } from 'react';

const useSpaceCreate = () => {
  const [isCreateSpace, setIsCreateSpace] = useState(true);

  const onCreateSpace = () => {
    setIsCreateSpace(true);
  };

  return { isCreateSpace, onCreateSpace };
};

export default useSpaceCreate;
