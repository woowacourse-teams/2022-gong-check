import { AxiosError } from 'axios';
import { useRef, useState } from 'react';
import { useMutation } from 'react-query';

import useModal from '@/hooks/useModal';
import useSections from '@/hooks/useSections';
import useToast from '@/hooks/useToast';

import apiImage from '@/apis/image';

const DEFAULT_NO_IMAGE = 'https://velog.velcdn.com/images/cks3066/post/7f506718-7a3c-4d63-b9ac-f21b6417f3c2/image.png';

interface SectionDetailModalProps {
  target: 'section' | 'task';
  sectionIndex: number;
  taskIndex?: number;
  previousImageUrl: string;
  previousDescription: string;
}

const useSectionDetailModal = (props: SectionDetailModalProps) => {
  const { target, sectionIndex, taskIndex = 0, previousImageUrl, previousDescription } = props;

  const { closeModal } = useModal();
  const { openToast } = useToast();

  const { getSectionInfo, getTaskInfo, editSectionDetail, editTaskDetail } = useSections();

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

  return {
    getSectionInfo,
    getTaskInfo,
    fileInput,
    isDisabledButton,
    onChangeImage,
    onChangeText,
    onClickSaveButton,
    closeModal,
    imageUrl,
    description,
  };
};

export default useSectionDetailModal;
