<h1>Feign Error Decoder ile Servisler Arası Hata Yakalama</h1>

Feign, RESTful servislerle çalışmak için kullanılan bir Java HTTP istemci kütüphanesidir. Feign Error Decoder, Feign
istekleri sırasında oluşan hataları yakalamak ve işlemek için kullanılan bir bileşendir.

<h3>Kullanımı</h3>

Feign Error Decoder kullanmak için öncelikle ErrorDecoder arayüzünü uygulamanız gerekmektedir. Aşağıda örnek bir
FeignErrorDecoder sınıfı verilmiştir:

<pre>
<code>
public class FeignErrorDecoder implements ErrorDecoder {
    private ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        ErrorBody message = null;
        try {
            System.out.println(response.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try (InputStream bodyIs = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            message = mapper.readValue(bodyIs, ErrorBody.class);
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }

        return switch (response.status()) {
            case 400 -> new BadRequestException(message.getErrorDescription());
            case 403 -> new ForbiddenException(message.getErrorDescription());
            case 404 -> new NotFoundException(message.getErrorDescription());
            case 409 -> new ConflictException(message.getErrorDescription());
            case 412 -> new PreconditionException(message.getErrorDescription());
            case 429 -> new TooManyRequestException(message.getErrorDescription());
            case 433 -> new DCBusinessException(message.getErrorDescription(), message.getErrorCode());
            case 500 -> new InternalServerException(message.getErrorDescription());
            case 502 -> new DCRequestedServiceDownException(message.getErrorDescription(), 434);
            default -> errorDecoder.decode(methodKey, response);
        };
    }
}
</code>
</pre>

Bu sınıf, Feign istekleri sırasında oluşan hataları yakalamak için kullanılabilir.

<h1>
WebClient Filter ile Servisler Arası Hata Yakalama
</h1>

WebClient, Spring Framework 5 ile birlikte gelen, asenkron HTTP istekleri yapmak için kullanılan bir kütüphanedir.
WebClient Filter, WebClient istekleri sırasında oluşan hataları yakalamak ve işlemek için kullanılan bir bileşendir.
Örneğin :
<pre>
<code>
@Bean
public WebClient serviceTwoWebClient() {
    return WebClient
            .builder()
            .baseUrl(serviceTwoBaseUrl)
            .filter(ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
                if (clientResponse.statusCode().isError()) {
                    Mono<ErrorBody> response = clientResponse.bodyToMono(ErrorBody.class);
                    return switch (clientResponse.statusCode().value()){
                        case 400 -> clientResponse.bodyToMono(ErrorBody.class).flatMap(errorBody -> Mono.error(new BadRequestException(errorBody.getErrorDescription())));
                        case 403 -> clientResponse.bodyToMono(ErrorBody.class).flatMap(errorBody -> Mono.error(new ForbiddenException(errorBody.getErrorDescription())));
                        case 404 -> clientResponse.bodyToMono(ErrorBody.class).flatMap(errorBody -> Mono.error(new NotFoundException(errorBody.getErrorDescription())));
                        case 409 -> clientResponse.bodyToMono(ErrorBody.class).flatMap(errorBody -> Mono.error(new ConflictException(errorBody.getErrorDescription())));
                        case 412 -> clientResponse.bodyToMono(ErrorBody.class).flatMap(errorBody -> Mono.error(new PreconditionException(errorBody.getErrorDescription())));
                        case 429 -> clientResponse.bodyToMono(ErrorBody.class).flatMap(errorBody -> Mono.error(new TooManyRequestException(errorBody.getErrorDescription())));
                        case 433 -> clientResponse.bodyToMono(ErrorBody.class).flatMap(errorBody -> Mono.error(new DCBusinessException(errorBody.getErrorDescription(),errorBody.getErrorCode())));
                        case 500 -> clientResponse.bodyToMono(ErrorBody.class).flatMap(errorBody -> Mono.error(new InternalServerException(errorBody.getErrorDescription())));
                        case 502 -> clientResponse.bodyToMono(ErrorBody.class).flatMap(errorBody -> Mono.error(new DCRequestedServiceDownException(errorBody.getErrorDescription(), 434)));
                        default -> Mono.just(clientResponse);
                    };
                }
                else {
                    return Mono.just(clientResponse);
                }
            }))
            .build();
}
</code>
</pre>