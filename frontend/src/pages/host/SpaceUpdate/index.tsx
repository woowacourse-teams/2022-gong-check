import { AxiosError } from 'axios';
import { useEffect, useState } from 'react';
import { useMutation, useQuery } from 'react-query';
import { useParams } from 'react-router-dom';

import ImageBox from '@/components/host/ImageBox';
import useImageBox from '@/components/host/ImageBox/useImageBox';
import SpaceInfo from '@/components/host/SpaceInfo';

import useSpaceForm from '@/hooks/useSpaceForm';

import apiSpace from '@/apis/space';

import styles from './styles';

const SpaceUpdate: React.FC = () => {
  const { spaceId } = useParams();

  const { data } = useQuery(['space', spaceId], () => apiSpace.getSpace(spaceId), { suspense: true });

  const { onSubmitUpdateSpace } = useSpaceForm();
  const { imageUrl, onChangeImg } = useImageBox(data?.imageUrl);

  return (
    <div css={styles.layout}>
      <form onSubmit={e => onSubmitUpdateSpace(e, imageUrl, spaceId)} encType="multipart/form-data">
        <SpaceInfo type={'update'} data={data}>
          <ImageBox type={'update'} imageUrl={imageUrl} onChangeImg={onChangeImg} />
        </SpaceInfo>
      </form>
    </div>
  );
};

export default SpaceUpdate;
