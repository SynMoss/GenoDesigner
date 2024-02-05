import JSEncrypt from 'jsencrypt/bin/jsencrypt.min'


const publicKey = `MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDO5fzIVcfCVZCNvKI+LTu//6+OgbTRjctxhWXxDisdZNsyKx+0lbVnVwsl4ZwFZlTocMvkx065yZl
epfnmffqyGOU36em+ZdK1neqGvcRiJistq/AF7pA2KASHS7Fg634mO6NHYkuepNeWzkcCVlGBR8vF+MUZmMc1CdzDJlnWJQIDAQAB`

const privateKey = `MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM7l/MhVx8JVkI28oj4tO7//r46BtNGNy3GFZfEOKx1k2zIrH7SVtWdXCyXhnAVmVOhwy+THTrnJmV6l+eZ9+rIY5Tfp6b5l0rWd6oa9xGImKy2r8AXukDY
oBIdLsWDrfiY7o0diS56k15bORwJWUYFHy8X4xRmYxzUJ3MMmWdYlAgMBAAECgYADJPOmh8c1aN99h/RfRYi27kfYvpfk52bEHubhtvloxPOf24/ryqqrG2OL+9XHLzrzHWO74luL70hpa9RUcG2y6DB3zHsXwrEmX5N0z3nBL36vQ1DDANHGd8gg8uF
FIyC0UUYLqdyz63jdZyxY+1FdclVUbqPdggNlXOvkCALlMQJBAPrmKl5EHoUXHpx6EN35/7gjtPYheaUthwkSVnBWuRwdAUwA1RrCGDrRDuyEGlJziD51npF+C+5Fd0usOxeuqPMCQQDTGtCkm0gHkSnW0QDxoyVzM//oLZEFVHlCQzlWKEYJgi+8plFn
UIVXUVupyxdk70EU9dQiUUvl569EjNEUFcqHAkEAgK+5H2x08vJuwvdg21m0rW+zC2MUD4KQdjivmSMCAsdluOPcacMPxmsf6nO/RHb5dH/DewBZgjai/28xR+5PvQJAA33iDZbz
7gwcoQz78wM7z1gA15pJV8k56DqsbBxmbCnXGEu39PmD+xnBZmGRlX3PmcUnKRonIzpdz8h0fNyPaQJAbFj1BVH3a17cls6RgwdfFMookxbzD8WDf5Z73uoVoAY573dD/o5BuZ6xd0dVUq+pBhS3A8ufE79zzkYB6so+cg==`

export function encrypt(txt) {
  const encryptor = new JSEncrypt()
  encryptor.setPublicKey(publicKey)
  return encryptor.encrypt(txt) 
}

export function decrypt(txt) {
  const encryptor = new JSEncrypt()
  encryptor.setPrivateKey(privateKey)
  return encryptor.decrypt(txt)
}

