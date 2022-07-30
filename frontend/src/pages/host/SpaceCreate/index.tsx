import useSpaceCreate from './useSpaceCreate';

import Button from '@/components/common/Button';
import SpaceInfo from '@/components/host/SpaceInfo';

import styles from './styles';

const SpaceCreate: React.FC = () => {
  const { isCreateSpace, onSubmitMultiPartFormData, onCreateSpace } = useSpaceCreate();

  return (
    <div css={styles.layout}>
      {isCreateSpace ? (
        <form onSubmit={onSubmitMultiPartFormData} encType="multipart/form-data">
          <SpaceInfo type={'create'} />
        </form>
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
