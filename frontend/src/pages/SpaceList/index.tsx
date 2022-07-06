/**  @jsxImportSource @emotion/react */
import PageTitle from '@/components/_common/PageTitle';

import InputModal from '@/components/InputModal';
import SpaceCard from '@/components/SpaceCard';

import useModal from '@/hooks/useModal';

import styles from './styles';

const spaces = [
  {
    id: 1,
    name: '잠실',
    imageURL:
      'https://images.unsplash.com/photo-1611186871348-b1ce696e52c9?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1740&q=80',
  },
  {
    id: 2,
    name: '선릉',
    imageURL:
      'https://images.unsplash.com/photo-1517336714731-489689fd1ca8?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1626&q=80',
  },
];

const SpaceList = () => {
  const { isShowModal, closeModal } = useModal();
  return (
    <div css={styles.layout}>
      <PageTitle>
        장소 목록 (<span>2</span>)
      </PageTitle>
      <div css={styles.contents}>
        {spaces.map(space => (
          <SpaceCard spaceName={space.name} imageURL={space.imageURL} key={space.id} id={space.id} />
        ))}
      </div>
      {isShowModal && (
        <InputModal
          title="비밀번호 입력"
          detail="해당 공간의 관계자만 접근할 수 있습니다."
          placeholder="비밀번호를 입력해주세요."
          buttonText="확인"
          closeModal={closeModal}
        />
      )}
    </div>
  );
};

export default SpaceList;
