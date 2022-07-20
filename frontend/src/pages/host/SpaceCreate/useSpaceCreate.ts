import { AxiosError } from 'axios';
import { useState } from 'react';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';

import apiSpace from '@/apis/space';

const useSpaceCreate = () => {
  const navigate = useNavigate();

  const [isCreateSpace, setIsCreateSpace] = useState(true);

  const { mutate: submitFormData } = useMutation((formData: any) => apiSpace.postNewSpace({ formData }), {
    onSuccess: res => {
      const locationSplited = res.headers.location.split('/');
      const spaceId: string = locationSplited[locationSplited.length - 1];

      navigate(`/host/manage/${spaceId}`);
    },
    onError: (err: AxiosError<{ message: string }>) => {
      alert(err.response?.data.message);
    },
  });

  const onSubmitMultiPartFormData = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const form = e.target as HTMLFormElement;
    const formData = new FormData();

    const imageInput = form['imageInput'];
    const nameInput = form['nameInput'];
    const files = imageInput.files;
    const file = files[0];
    const name = nameInput.value;

    formData.append('name', name);
    if (files.length) formData.append('image', file);

    submitFormData(formData);
  };

  const onCreateSpace = () => {
    setIsCreateSpace(true);
  };

  return { isCreateSpace, onSubmitMultiPartFormData, onCreateSpace };
};

export default useSpaceCreate;
