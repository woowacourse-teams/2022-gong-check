import { useState } from 'react';

import Button from '@/components/_common/Button';

import SpaceInfo from '@/components/host/SpaceInfo';

import styles from './styles';

const SpaceCreate: React.FC = () => {
  const [isCreateSpace, setIsCreateSpace] = useState(false);

  const spaceName = '';
  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    console.log('click');
  };

  const handleCreateSpace = () => {
    setIsCreateSpace(true);
  };

  return (
    <div css={styles.layout}>
      {isCreateSpace ? (
        <SpaceInfo onSubmit={handleSubmit} inputText={spaceName} />
      ) : (
        <div css={styles.contents}>
          <p css={styles.text}>등록된 공간이 없습니다.</p>
          <Button onClick={handleCreateSpace}>새로운 공간 추가</Button>
        </div>
      )}
    </div>
  );
};

export default SpaceCreate;
