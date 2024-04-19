# aprts-service-app 
> 자바 너무 오랜만에 해서 연습용 코드를 작성하고 싶은데,
> </br>더미데이터도 귀찮고 뭐해야 될지도 모르겠어서 CI4 로 작성된 운영 코드를 스프링으로 옮김
> </br>다소 간략화하고... 비즈니스 로직도 제거해서 ㄱ구현햇다 (거의 제거햇다)
> </br>컬럼도 시바꺼 그냥 100줄이 넘어가길래 생략했다 (db 마이그레이션은 그대로 가져옴)

# entity 생성
### 1. 복합키 IdClass vs @EmbeddedId
* 특징</br>
IdClass : 데이터베이스에 가까움, 식별자 클래스 테이블에도 필드를 다 선언해야 됨</br>
@EmbeddedId : 객체지향에 가까움, 식별자 클래스를 필드로 선언해버리면 됨
* 필드를 다 선언하기 귀찮지만 (몇개안되긴함) 기본적으로 테이블이나 비즈니스 로직이 복잡해서 간결하게 사용하기 위해 IdClass
* 사용방법</br>
식별자 클래스 : @Embedded, 디폴트 생성자, Serializable 상속, equals, hashCode 필요</br>
해당 엔티티 : @IdClass
### 2. AuditingEntityListener
썼다