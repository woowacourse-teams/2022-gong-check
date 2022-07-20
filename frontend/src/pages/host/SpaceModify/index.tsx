// import { useQuery } from 'react-query';
import { useParams } from 'react-router-dom';

// import SpaceInfo from '@/components/host/SpaceInfo';

// import apis from '@/apis';

// import styles from './styles';

const SpaceModify: React.FC = () => {
  const { spaceId } = useParams();

  return <div>spaceId:{spaceId}, Sprint3때 구현할 예정</div>;
  // const { data } = useQuery(['space', spaceId], () => apis.getSpace({ spaceId }), { suspense: true });

  // // TODO 수정 해야함
  // if (!data) return <></>;

  // return (
  //   <div css={styles.layout}>
  //     <SpaceInfo data={data} />
  //   </div>
  // );
};

export default SpaceModify;
