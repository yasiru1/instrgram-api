package app.test.yasiru.andoridtest.POJO;



// to get user data
public class Data  {

    private Images images;
    private User user;

    public Images getImages() {
        return images;
    }

    public User getUser() {
        return user;
    }

    //user data
    public class User {

        private String profile_picture;
        private String full_name;

        public String getProfile_picture() {
            return profile_picture;
        }

        public String getFull_name() {
            return full_name;
        }
    }
    // image data
    public class Images {

        private Standard_resolution standard_resolution;

        public Standard_resolution getStandard_resolution() {
            return standard_resolution;
        }
        // Standard_resolution image url
        public class Standard_resolution {

            private String url;

            public String getUrl() {
                return url;
            }
        }
    }
}
