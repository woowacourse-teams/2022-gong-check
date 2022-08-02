import { AxiosError } from 'axios';
import { useRef, useState } from 'react';
import { BiX } from 'react-icons/bi';
import { useMutation } from 'react-query';

import Button from '@/components/common/Button';
import Dimmer from '@/components/common/Dimmer';

import useModal from '@/hooks/useModal';
import useSections from '@/hooks/useSections';
import useToast from '@/hooks/useToast';

import apiImage from '@/apis/image';

import ModalPortal from '@/portals/ModalPortal';

import styles from './styles';

const DEFAULT_NO_IMAGE = 'https://velog.velcdn.com/images/cks3066/post/7f506718-7a3c-4d63-b9ac-f21b6417f3c2/image.png';

interface SectionDetailModalProps {
  target: 'section' | 'task';
  sectionIndex: number;
  taskIndex?: number;
  previousImageUrl: string;
  previousDescription: string;
}

const SectionDetailModal: React.FC<SectionDetailModalProps> = ({
  target,
  sectionIndex,
  taskIndex = 0,
  previousImageUrl,
  previousDescription,
}) => {
  const { closeModal } = useModal();
  const { openToast } = useToast();

  const { editSectionDetail, editTaskDetail } = useSections();

  const fileInput = useRef<HTMLInputElement>(null);

  const [imageUrl, setImageUrl] = useState(previousImageUrl || DEFAULT_NO_IMAGE);
  const [description, setDescription] = useState(previousDescription);
  const [isDisabledButton, setIsDisabledButton] = useState(true);

  const { mutateAsync: uploadImage } = useMutation((formData: FormData) => apiImage.postImageUpload(formData), {
    onSuccess: data => {
      setImageUrl(data.imageUrl);
    },
    onError: (err: AxiosError<{ message: string }>) => {
      openToast('ERROR', `${err.response?.data.message}`);
    },
  });

  const onChangeImage = async (e: React.ChangeEvent<HTMLInputElement>) => {
    if (!e.target.files) return;

    const imageFile = e.target.files[0];

    const formData = new FormData();
    formData.append('name', imageFile);
    formData.append('filename', imageFile.name);

    uploadImage(formData);

    const isAbleButtonState = !!imageFile || !!description;
    setIsDisabledButton(!isAbleButtonState);
  };

  const onChangeText = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setDescription(e.target.value);

    const isAbleButtonState = imageUrl !== DEFAULT_NO_IMAGE || !!e.target.value;
    setIsDisabledButton(!isAbleButtonState);
  };

  const onClickSaveButton = () => {
    if (target === 'section') editSectionDetail(sectionIndex, imageUrl, description);
    if (target === 'task') editTaskDetail(sectionIndex, taskIndex, imageUrl, description);

    openToast('SUCCESS', '저장에 성공하였습니다.');
    closeModal();
  };

  return (
    <ModalPortal>
      <Dimmer mode="full" isAbleClick={false}>
        <div css={styles.container}>
          <BiX size={36} onClick={closeModal} />
          <h1>섹션 이름</h1>
          <img css={styles.image} src={imageUrl} alt="" onClick={() => fileInput.current?.click()} />
          <input type="file" accept="image/*" ref={fileInput} onChange={onChangeImage} />
          <div css={styles.description}>
            <textarea
              rows={4}
              value={description}
              placeholder="사용자가 확인할 안내사항을 입력주세요. (120자이내)"
              maxLength={120}
              onChange={onChangeText}
            />
            <span>{description?.length || 0}/120</span>
          </div>
          <Button css={styles.saveButton(isDisabledButton)} disabled={isDisabledButton} onClick={onClickSaveButton}>
            저장
          </Button>
        </div>
      </Dimmer>
    </ModalPortal>
  );
};

export default SectionDetailModal;
