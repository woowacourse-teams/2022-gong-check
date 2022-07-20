import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import Button from '@/components/common/Button';
import SpaceInfo from '@/components/host/SpaceInfo';

import styles from './styles';

const SpaceCreate: React.FC = () => {
  const navigate = useNavigate();

  const [isCreateSpace, setIsCreateSpace] = useState(true);

  const onSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const form = e.target as HTMLFormElement;

    // TODO: SpaceInfo submit시, API 호출 등 제어권을 페이지 컴포넌트가 가진다.
    // 추후 API 연동 로직을 구현해야 한다.

    const imageInput = form['imageInput'];
    const nameInput = form['nameInput'];

    // TODO image이 없을 경우 file => undefined, src => 에러남
    // const file = imageInput.files[0];
    // const src = URL.createObjectURL(file);

    // const name = nameInput.value;

    // api 호출 후, Location : /api/spaces/{spaceId} 이걸 받아와 헤더의 location으로 받아온 뒤, 페이지 이동 gogo
    // navigate(`/host/manage/${spaceId}`);
  };

  const onCreateSpace = () => {
    setIsCreateSpace(true);
  };

  return (
    <div css={styles.layout}>
      {isCreateSpace ? (
        <SpaceInfo onSubmit={onSubmit} />
      ) : (
        <div css={styles.contents}>
          <p css={styles.text}>등록된 공간이 없습니다.</p>
          <Button onClick={onCreateSpace}>새로운 공간 추가</Button>
        </div>
      )}
    </div>
  );
};

export default SpaceCreate;
