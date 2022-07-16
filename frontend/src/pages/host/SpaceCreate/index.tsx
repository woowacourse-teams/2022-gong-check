import { useState } from 'react';

import Button from '@/components/_common/Button';

import SpaceInfo from '@/components/host/SpaceInfo';

import styles from './styles';

const SpaceCreate: React.FC = () => {
  const [isCreateSpace, setIsCreateSpace] = useState(false);

  const spaceName = '';
  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    // TODO: SpaceInfo submit시, API 호출 등 제어권을 페이지 컴포넌트가 가진다.
    // 추후 API 연동 로직을 구현해야 한다.
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
