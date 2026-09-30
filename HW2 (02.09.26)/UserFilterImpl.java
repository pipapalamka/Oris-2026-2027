package DZ2;

public class UserFilterImpl implements UserFilter{
    @Override
    public boolean filter(User user){
        return user.getName().length() > 3 && user.getAge() < 52;
    }

}
