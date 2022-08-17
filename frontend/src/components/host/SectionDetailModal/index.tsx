import useSectionDetailModal from './useSectionDetailModal';
import { BiX } from 'react-icons/bi';

import Button from '@/components/common/Button';
import Dimmer from '@/components/common/Dimmer';

import ModalPortal from '@/portals/ModalPortal';

import styles from './styles';

interface SectionDetailModalProps {
  target: 'section' | 'task';
  sectionIndex: number;
  taskIndex?: number;
  previousImageUrl: string;
  previousDescription: string;
}

const SectionDetailModal: React.FC<SectionDetailModalProps> = props => {
  const { target, sectionIndex, taskIndex } = props;

  const {
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
    isLoadingImage,
    onClickClose,
  } = useSectionDetailModal(props);

  return (
    <ModalPortal>
      <Dimmer mode="full" isAbleClick={false}>
        <div css={styles.container(isLoadingImage)}>
          <BiX size={36} onClick={onClickClose} />
          <h1>
            {target === 'section'
              ? getSectionInfo(sectionIndex).name
              : getTaskInfo(sectionIndex, taskIndex as number).name}
          </h1>
          <img
            css={styles.image(isLoadingImage)}
            src={imageUrl}
            alt=""
            onClick={() => !isLoadingImage && fileInput.current?.click()}
          />
          <input
            type="file"
            accept="image/gif, image/jpg, image/jpeg, image/png, image/svg"
            ref={fileInput}
            onChange={onChangeImage}
          />
          <div css={styles.description(isLoadingImage)}>
            <textarea
              rows={4}
              value={description}
              placeholder="사용자가 확인할 안내사항을 입력주세요. (128자이내)"
              maxLength={128}
              onChange={onChangeText}
            />
            <span>{description?.length || 0}/128</span>
          </div>
          <Button
            css={styles.saveButton(isDisabledButton, isLoadingImage)}
            disabled={isDisabledButton}
            onClick={() => !isLoadingImage && onClickSaveButton}
          >
            저장
          </Button>
        </div>
      </Dimmer>
    </ModalPortal>
  );
};

export default SectionDetailModal;
