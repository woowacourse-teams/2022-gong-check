import { AxiosError } from 'axios';
import { useMutation, useQuery } from 'react-query';
import { useNavigate, useParams } from 'react-router-dom';

import SpaceInfo from '@/components/host/SpaceInfo';

import useToast from '@/hooks/useToast';

import apiSpace from '@/apis/space';

import DUMMY_LOCAL_IMAGE_URL from '@/assets/homeCover.png';

import styles from './styles';

const SpaceUpdate: React.FC = () => {
  const navigate = useNavigate();
  const { spaceId } = useParams();
  const { openToast } = useToast();

  const { data } = useQuery(['space', spaceId], () => apiSpace.getSpace(spaceId), { suspense: true });

  const { mutate: submitFormData } = useMutation((formData: any) => apiSpace.putSpace(formData, spaceId), {
    onSuccess: () => {
      openToast('SUCCESS', '공간 정보가 수정 되었습니다.');
      navigate(`/host/manage/${spaceId}`);
    },
    onError: (err: AxiosError<{ message: string }>) => {
      openToast('ERROR', `${err.response?.data.message}`);
    },
  });

  const convertURLtoFile = async (url: string) => {
    const response = await fetch(url);
    const data = await response.blob();
    const ext = url.split('.').pop();
    const filename = url.split('/').pop() || '';
    const metadata = { type: `image/${ext}` };

    return new File([data], filename, metadata);
  };

  const onSubmitUpdateMultiPartFormData = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const form = e.target as HTMLFormElement;
    const formData = new FormData();

    if (data?.imageUrl) {
      // TODO: S3 도입되면 data.imageUrl로 이미지 file 객체를 생성해야 한다.
      // await convertURLtoFile(data?.imageUrl).then(file => {
      await convertURLtoFile(DUMMY_LOCAL_IMAGE_URL).then(file => {
        formData.append('image', file);
      });
    }

    const imageInput = form['imageInput'];
    const nameInput = form['nameInput'];
    const files = imageInput.files;
    const file = files[0];
    const name = nameInput.value;

    formData.append('request', new Blob([JSON.stringify({ name })], { type: 'application/json' }));
    if (files.length) formData.append('image', file);

    submitFormData(formData);
  };

  return (
    <div css={styles.layout}>
      <SpaceInfo data={data} onSubmit={onSubmitUpdateMultiPartFormData} />
    </div>
  );
};

export default SpaceUpdate;
