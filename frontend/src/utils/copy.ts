export const clip = (url: string): void => {
  const textarea = document.createElement('textarea');
  document.body.appendChild(textarea);
  textarea.value = url;
  textarea.select();
  document.execCommand('copy');
  document.body.removeChild(textarea);
};
