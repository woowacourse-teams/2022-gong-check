/**  @jsxImportSource @emotion/react */
import SpaceCard from '../../components/SpaceCard';
import PageTitle from '../../components/_common/PageTitle';
import styles from './styles';

const spaces = [
  {
    name: '선릉',
    imageURL:
      'https://images.unsplash.com/photo-1611186871348-b1ce696e52c9?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1740&q=80',
  },
  {
    name: '잠실',
    imageURL:
      'https://images.unsplash.com/photo-1517336714731-489689fd1ca8?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1626&q=80',
  },
];

const SpaceList = () => {
  return (
    <div css={styles.layout}>
      <PageTitle>
        장소 목록 (<span>2</span>)
      </PageTitle>
      <div css={styles.contents}>
        {spaces.map((space, index) => (
          <SpaceCard spaceName={space.name} imageURL={space.imageURL} key={index} />
        ))}
      </div>
    </div>
  );
};

export default SpaceList;